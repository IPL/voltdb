/* This file is part of VoltDB.
 * Copyright (C) 2008-2014 VoltDB Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package org.voltdb.planner;

import org.voltdb.benchmark.tpcc.TPCCProjectBuilder;

public class TestSubqueryPlansTPCC extends PlannerTestCase {

    @Override
    protected void setUp() throws Exception {
        boolean planForSinglePartitionFalse = false;
        setupSchema(TPCCProjectBuilder.class.getResource("tpcc-ddl.sql"), "testplanstpcc", planForSinglePartitionFalse);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

//    public void testNonCorrelatedSubqeryInFrom() {
//    	compile("select col from (select W_TAX as col from WAREHOUSE) as t limit 1;");
//    }
    
    public void testNonCorrelatedSubqeriesInFrom() {
    	String sql = "select * " +
    			"from (select W_TAX as col1 " +
    			"      from WAREHOUSE) as t1 " +
    			"     left outer join (select W_TAX as col2 " +
    			"                      from WAREHOUSE) as t2 " +
    			"     on t1.col1 = t2.col2 " +
    			"limit 1;";
    	compile(sql);
    }
    
//    public void a_testNonCorrelatedSubqeryInSelect() {
    	//compile("select W_TAX, (select max(i_id) from item) as col2 from WAREHOUSE limit 1;");
//    }
    
//    public void testCorrelatedSubqery() {
//    	compile("select * from orders where O_ID > (select avg(I_PRICE) from item where o_id = i_id limit 1) limit 1;");
//    }
}
