package org.scalameter



import javax.management._
import collection._
import JavaConversions._



package object utils {

  def withGCNotification[T](eventhandler: Notification => Any) = new {
    import util.Properties.javaVersion
    def apply(body: =>T): T = if (javaVersion.startsWith("1.7")) {
      val gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans
      val listeners = for (gcbean <- gcbeans) yield {
        val listener = new NotificationListener {
          def handleNotification(n: Notification, handback: Object) {
            eventhandler(n)
          }
        }
        val emitter = gcbean.asInstanceOf[NotificationEmitter]
        emitter.addNotificationListener(listener, null, null)
        (emitter, listener)
      }
      val result = body
      for ((emitter, l) <- listeners) emitter.removeNotificationListener(l)
      result
    } else {
      body
    }
  }

}