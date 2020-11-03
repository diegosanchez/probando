package Elementos.Ing.AgileTasks.persistencia.runner

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

class HibernateSessionFactoryProvider private constructor() {

    private val sessionFactory: SessionFactory?

    init {
        val env = System.getenv()
        val user = env.getOrDefault("MYSQL_USERNAME", "root")
        val password = env.getOrDefault("MYSQL_PASSWORD","root")
//        val host = "localhost"
//        val dataBase = env.getOrDefault("MYSQL_DATABASE", "agiletasks_db")

        val configuration = Configuration()
        configuration.configure("hibernate.cfg.xml")
        configuration.setProperty("hibernate.connection.username", user)
        configuration.setProperty("hibernate.connection.password", password)
        this.sessionFactory = configuration.buildSessionFactory()
    }

    fun createSession(): Session {
        return this.sessionFactory!!.openSession()
    }

    companion object {

        private var INSTANCE: HibernateSessionFactoryProvider? = null

        val instance: HibernateSessionFactoryProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE = HibernateSessionFactoryProvider()
                }
                return INSTANCE!!
            }

        fun destroy() {
            if (INSTANCE != null && INSTANCE!!.sessionFactory != null) {
                INSTANCE!!.sessionFactory!!.close()
            }
            INSTANCE = null
        }
    }
}
