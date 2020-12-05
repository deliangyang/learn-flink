package com.test.log

import org.apache.log4j.Logger

object Log {

  private val logRoot: Logger = Logger.getRootLogger
  private val log1: Logger = Logger.getLogger("log1")

  def main(args: Array[String]): Unit = {
    logRoot.debug("This is debug message.");
    log1.debug("This is debug message.");
    // 记录info级别的信息
    logRoot.info("This is info message.");
    log1.info("This is info message.");
    // 记录error级别的信息
    logRoot.error("This is error message.");
    log1.error("This is error message.");
  }
}
