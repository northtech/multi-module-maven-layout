The Oracle JDBC Pool (`oracle.jdbc.pool.OracleDataSource`) contains a longstanding flaw, loading its static housekeeping
code and threads in the classloader of the first application which references the data source, rather than in the
(container) classloader which loaded the driver. In other words, the first application to use a DataSource becomes the
host of the database pool, even after the app has otherwise terminated, creating a classloader leak.

Oracle is aware of this bug, but dismisses it as "not significant, since it is only a single application".

Potential workarounds could include creating a minimal application which is loaded as the first application on server
startup, simply referencing a JDBI data source and becoming the host, to let other applications undeploy cleanly.

It is perhaps also possible to use Thread.currentThread().setContextClassLoader(...) to change to a classloader for a
known class from the Tomcat lib, reference the DataSource to force a load in that classloader, and change the
classloader back, though this is untested.

But the easiest is to just not use the Oracle pool, replacing it with, say, Tomcat's implementation of a JDBC pool. 