/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.beam.runners.spark.aggregators.metrics.sink;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;

import java.util.Properties;

import org.apache.beam.runners.spark.aggregators.metrics.WithNamedAggregatorsSupport;
import org.apache.spark.metrics.sink.Sink;

/**
 * An in-memory {@link Sink} implementation for tests.
 */
public class InMemoryMetrics implements Sink {

  private static WithNamedAggregatorsSupport extendedMetricsRegistry;
  private static MetricRegistry internalMetricRegistry;

  public InMemoryMetrics(final Properties properties,
                         final MetricRegistry metricRegistry,
                         final org.apache.spark.SecurityManager securityMgr) {
    extendedMetricsRegistry = WithNamedAggregatorsSupport.forRegistry(metricRegistry);
    internalMetricRegistry = metricRegistry;
  }

  @SuppressWarnings("unchecked")
  public static <T> T valueOf(final String name) {
    final T retVal;

    if (extendedMetricsRegistry != null
        && extendedMetricsRegistry.getGauges().containsKey(name)) {
      retVal = (T) extendedMetricsRegistry.getGauges().get(name).getValue();
    } else {
      retVal = null;
    }

    return retVal;
  }

  public static void clearAll() {
    if (internalMetricRegistry != null) {
      internalMetricRegistry.removeMatching(MetricFilter.ALL);
    }
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public void report() {

  }

}
