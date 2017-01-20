/* JAI-Ext - OpenSource Java Advanced Image Extensions Library
 *    http://www.geo-solutions.it/
 *    Copyright 2016 GeoSolutions


 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * The code was modified from the JAITools project, with permission 
 * from the author Michael Bedward.
 */ 

package it.geosolutions.jaiext.numeric;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants for the statistics supported by the {@linkplain SampleStats} and
 * {@linkplain StreamingSampleStats} classes.
 * 
 * @author Michael Bedward
 * @since 1.0
 * @version $Id$
 */
public enum Statistic {

    /** Arithmetic mean */
    MEAN("mean", "arithmetic mean", false),

    /** Exact median */
    MEDIAN("median", "median sample value", false),

    /** Approximate median calculated with the 'remedian' algorithm
     * of Rousseeuw et al. This is only used with {@linkplain StreamingSampleStats}.
     */
    APPROX_MEDIAN("approx. median", "approximate median (remedian algorithm)", true),

    /** Minimum sample value */
    MIN("min", "minimum value", true),

    /** Maximum sample value */
    MAX("max", "maximum value", true),
            
    /** Range (maximum - minimum) */
    RANGE("range", "sample range", true),

    /** Sample standard deviation */
    SDEV("sdev", "sample standard deviation", false),

    /** Sum of valid values */
    SUM("sum", "sum of valid values", true),
    
    /** Sample variance */
    VARIANCE("variance", "sample variance", false);

    private static final Map<String, Statistic> lookup;
    static {
        lookup = new HashMap<String, Statistic>();
        for (Statistic stat : Statistic.values()) {
            lookup.put(stat.name, stat);
        }
    }

    private String name;
    private String desc;
    private boolean supportsIntResult;

    /**
     * Private constructor
     */
    private Statistic(String name, String desc, boolean supportsIntResult) {
        this.name = name;
        this.desc = desc;
        this.supportsIntResult = supportsIntResult;
    }

    /**
     * Returns the short name of this statistic
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns a brief description of the statistic
     * @return 
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Tests if this statistic can return an integral result
     * when working with integral sample data.
     * 
     * @return {@code true} if an integral result can be returned;
     *         {@code false} otherwise
     */
    public boolean supportsIntegralResult() {
        return supportsIntResult;
    }

    /**
     * Gets a Statistic constant by name (case-insensitive).
     * 
     * @param name the statistic name
     * @return a Statistic instance or null if the name was
     *         was not matched
     */
    public static Statistic get(String name) {
        return lookup.get(name.toLowerCase());
    }
}
