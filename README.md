# play-scala-build-template

RUN
======
`sbt run` // access to http://localhost:9000
NOTE: 
application.confで`play.evolutions.autoApply=true`を指定しているので
DBのマイグレーションは自動で行われます。

ORM code-gen
=======
`sbt "scalikejdbc-gen users"`
http://scalikejdbc.org/

