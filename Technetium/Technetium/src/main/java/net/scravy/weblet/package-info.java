/**
 * Weblets provide an easier and more modern API for using the Java Servlet API.
 * <p>
 * Weblets do not produce a webpage themselves, but create a
 * {@link net.scravy.weblet.Response} which is than translated into a webpage
 * (or XML, or JSON, or anything), for example using XSL Transformations.
 * </p>
 * <p>
 * Some special Features of Weblets allow to utilize
 * {@link javax.script.ScriptEngine} as specified by JSR 223, that is, you can
 * integrate Python, Ruby, Clojure, ... Scripts into your Java Servlet Application
 * without any hassle. For doing so, simply specify a script resource as
 * {@link net.scravy.weblet.Handler} in a {@link net.scravy.weblet.Module}.
 * </p>
 * 
 * @author Julian Fleischer
 * @since 1.0
 */
package net.scravy.weblet;