
package com.ons.sml.businessMethods.methods

import org.apache.spark.sql.DataFrame
import uk.gov.ons.SparkTesting.{SparkSessionProvider, TestSparkContext}
import com.ons.sml.businessMethods.impl.LagImpl._

class Lag_Test extends TestSparkContext {
  /**
    *  Class containing Lag implementation tests
    */

  // Create test
  test("Lag_Test") {

    // Create DataFrame with input data
    val input_data = "./src/test/resources/sml/inputs/lag_data.json"
    val input_df = SparkSessionProvider.sparkSession.read.json(input_data).select("id", "t", "v")
    println("Input data frame:")
    input_df.show()

    // Create DataFrame with expected data
    val expected_data = "./src/test/resources/sml/outputs/expected_data.json"
    val expected_df = SparkSessionProvider.sparkSession.read.json(expected_data).select("id", "t", "v", "lagged1", "lagged2")
    println("Expected data frame:")
    expected_df.show()


    // Create DataFrame with input_data modified by lagFunc method
    val output_df:DataFrame = input_df.lagFunc(List("id"), List("t"), "v", 2)
    println("Output data frame:")
    output_df.show

    // Assert that test is successful
    assertResult(expected_df.collect()) {

      output_df.collect()
    }
  }
}
