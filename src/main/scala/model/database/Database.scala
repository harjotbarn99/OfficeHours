package model.database

import java.sql.{Connection, DriverManager, ResultSet}

import model.StudentInQueue


class Database extends DatabaseAPI{

//  val url = "jdbc:mysql://mysql/officehours?autoReconnect=true"
//  val username: String = sys.env("DB_USERNAME")
//  val password: String = sys.env("DB_PASSWORD")

    val url =  "jdbc:mysql://localhost/mysql?serverTimezone=UTC"
  val username: String = "root"
    val password: String = "get ur own password"

  var connection: Connection = DriverManager.getConnection(url, username, password)
//  setupTable()
  setupStudentTable()

  def setupStudentTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS queue (queueNo INT, ubit TEXT, timestamp BIGINT)")

  }


  override def addStudentToQueue(student: StudentInQueue): Unit = {
    val statement = connection.prepareStatement("INSERT INTO queue VALUE (?, ?, ?)")

    statement.setInt(1,student.queueNo)
    statement.setString(2, student.username)
    statement.setLong(3, student.timestamp)

    statement.execute()

  }


  /// work on this
  override def removeStudentFromQueue(username: String): Unit = {
    val statement = connection.prepareStatement("DELETE FROM queue WHERE ubit=?")

    statement.setString(1, username)

    statement.execute()
  }


  override def getQueue: List[StudentInQueue] = {
    val statement = connection.prepareStatement("SELECT * FROM queue")
    val result: ResultSet = statement.executeQuery()

    var queue: List[StudentInQueue] = List()

    while (result.next()) {
      val no = result.getInt("queueNo")
      val username = result.getString("ubit")
      val timestamp = result.getLong("timestamp")
      queue = new StudentInQueue(no,username, timestamp) :: queue
    }

    queue.reverse
  }






//  def setupTable(): Unit = {
//    val statement = connection.createStatement()
//    statement.execute("CREATE TABLE IF NOT EXISTS queue (username TEXT, timestamp BIGINT)")
//  }




//  override def addStudentToQueue(student: StudentInQueue): Unit = {
//    val statement = connection.prepareStatement("INSERT INTO queue VALUE (?, ?)")
//
//    statement.setString(1, student.username)
//    statement.setLong(2, student.timestamp)
//
//    statement.execute()
//  }


//  override def removeStudentFromQueue(username: String): Unit = {
//    val statement = connection.prepareStatement("DELETE FROM queue WHERE username=?")
//
//    statement.setString(1, username)
//
//    statement.execute()
//  }




}
