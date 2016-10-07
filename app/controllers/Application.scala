package controllers

import models.Users
import org.joda.time.DateTime
import play.api.mvc._
import scalikejdbc.AutoSession

class Application extends Controller {

  implicit val session = AutoSession

  def index = Action {

    val user = Users.find(1) match {
      case Some(x) => x
      case _ => Users.create("test",DateTime.now, DateTime.now)
    }
    Ok(user.toString)
  }


}
