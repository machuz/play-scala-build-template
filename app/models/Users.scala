package models

import scalikejdbc._
import org.joda.time.{DateTime}

case class Users(
  id: Int,
  name: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  deletedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Unit = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val tableName = "users"

  override val columns = Seq("id", "name", "created_at", "updated_at", "deleted_at")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    id = rs.get(u.id),
    name = rs.get(u.name),
    createdAt = rs.get(u.createdAt),
    updatedAt = rs.get(u.updatedAt),
    deletedAt = rs.get(u.deletedAt)
  )

  val u = Users.syntax("u")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.eq(u.id, id)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Users] = {
    withSQL(select.from(Users as u)).map(Users(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Users as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Users as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    createdAt: DateTime,
    updatedAt: DateTime,
    deletedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): Users = {
    val generatedKey = withSQL {
      insert.into(Users).namedValues(
        column.name -> name,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt,
        column.deletedAt -> deletedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Users(
      id = generatedKey.toInt,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt,
      deletedAt = deletedAt)
  }

  def batchInsert(entities: Seq[Users])(implicit session: DBSession = autoSession): Seq[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'deletedAt -> entity.deletedAt))
        SQL("""insert into users(
        name,
        created_at,
        updated_at,
        deleted_at
      ) values (
        {name},
        {createdAt},
        {updatedAt},
        {deletedAt}
      )""").batchByName(params: _*).apply()
    }

  def save(entity: Users)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      update(Users).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.deletedAt -> entity.deletedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Users)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Users).where.eq(column.id, entity.id) }.update.apply()
  }

}
