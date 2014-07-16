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

package org.voltdb.regressionsuites;

import java.io.IOException;

import org.voltdb.BackendTarget;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;
import org.voltdb.compiler.VoltProjectBuilder;
import org.voltdb_testprocs.regressionsuites.fixedsql.Insert;

/**
 * Tests for SQL that was recently (early 2012) unsupported, related to JSON
 * functions (which are supported by VoltDB but not HSQL or other databases).
 */

public class TestFunctionsForJSON extends RegressionSuite {

    /** Procedures used by this suite */
    static final Class<?>[] PROCEDURES = { Insert.class };

    /**
     * @return
     * @throws IOException
     * @throws NoConnectionsException
     * @throws ProcCallException
     */
    private void loadJS1(Client client) throws IOException, NoConnectionsException, ProcCallException
    {

        final String jstemplate = "{\n" +
                "    \"id\": %d,\n" +
                "    \"bool\": true,\n" +
                "    \"inner\": {\n" +
                "        \"veggies\": \"good for you\",\n" +
                "        \"贾鑫Vo\": \"wakarimasen\",\n" +
                "        \"second\": {\n" +
                "            \"fruits\": \"semi-good for you\",\n" +
                "            \"third\": {\n" +
                "                \"meats\": \"yum\",\n" +
                "                \"dairy\": \"%d\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"arr\": [\n" +
                "            0,\n" +
                "            %d\n" +
                "        ]\n" +
                "    },\n" +
                "    \"arr\": [\n" +
                "        0,\n" +
                "        %d,\n" +
                "        100\n" +
                "    ],\n" +
                "    \"arr3d\": [\n" +
                "        0,\n" +
                "        [\n" +
                "            1,\n" +
                "            [\n" +
                "                2,\n" +
                "                %d\n" +
                "            ]\n" +
                "        ],\n" +
                "        {\n" +
                "            \"veggies\": \"good for you\",\n" +
                "            \"dairy\": \"%d\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"tag\": \"%s\"\n" +
                "}";

        ClientResponse cr;
        cr = client.callProcedure("JS1.insert",1,String.format(jstemplate, 1, 1, 1, 1, 1, 1, "one"));
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",2,String.format(jstemplate, 2, 2, 2, 2, 2, 2, "two"));
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",3,String.format(jstemplate, 3, 3, 3, 3, 3, 3, "three"));
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",4,"{\"id\":4,\"bool\": false}");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",5,"{}");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",6,"[]");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",7,"{\"id\":7,\"funky\": null}");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",8, null);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("JS1.insert",9, "{\"id\":9, \"贾鑫Vo\":\"分かりません\"}");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
    }

    public void testFIELDFunction() throws Exception {
        ClientResponse cr;
        VoltTable result;
        Client client = getClient();
        loadJS1(client);

        cr = client.callProcedure("IdFieldProc", "id", "1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(1L,result.getLong(0));

        try {
            cr = client.callProcedure("IdFieldProc", "id", 1);
            assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        }
        catch ( ProcCallException pcex) {
            fail("parameter check failed");
        }

        try {
            cr = client.callProcedure("IdFieldProc", 1, "1");
            assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        }
        catch ( ProcCallException pcex) {
            fail("parameter check failed");
        }

        cr = client.callProcedure("IdFieldProc", "tag", "three");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(3L,result.getLong(0));

        cr = client.callProcedure("IdFieldProc", "bool", "false");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(4L,result.getLong(0));

        cr = client.callProcedure("IdFieldProc", "贾鑫Vo", "分かりません");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(9L,result.getLong(0));

        cr = client.callProcedure("NullFieldProc", "funky");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(9, result.getRowCount());

        cr = client.callProcedure("NullFieldProc", "id");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(3, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(5L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(6L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(8L,result.getLong(0));

        cr = client.callProcedure("InnerFieldProc", "贾鑫Vo" ,"wakarimasen");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(3, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(1L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(2L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(3L,result.getLong(0));

        cr = client.callProcedure("IdFieldProc", "arr", "[0,2,100]");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(2L,result.getLong(0));

        cr = client.callProcedure("@AdHoc", // test scalar not an object
                                  "SELECT FIELD(FIELD(DOC, 'id'), 'value') FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());

        cr = client.callProcedure("@AdHoc", // test array not an object
                                  "SELECT FIELD(FIELD(DOC, 'arr'), 'value') FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());
    }

    /** Used to test ENG-6620, part 1 (dotted path notation). */
    public void testFIELDFunctionWithDotNotation() throws Exception {
        ClientResponse cr;
        VoltTable result;
        Client client = getClient();
        loadJS1(client);

        cr = client.callProcedure("IdFieldProc", "inner.veggies", "good for you");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "inner.second.fruits", "semi-good for you");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "inner.second.third.meats", "yum");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "inner.second.third.dairy", "1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1}});
    }

    /** Used to test ENG-6620, part 2 (array index notation). */
    public void testFIELDFunctionWithIndexNotation() throws Exception {
        ClientResponse cr;
        VoltTable result;
        Client client = getClient();
        loadJS1(client);

        cr = client.callProcedure("IdFieldProc", "arr[0]", 0);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "arr[1]", 2);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{2}});

        cr = client.callProcedure("IdFieldProc", "arr[2]", 100);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "arr3d[1][0]", 1);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "arr3d[1][1][0]", 2);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "arr3d[1][1][1]", 3);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{3}});
    }

    /** Used to test ENG-6620, part 3 (dotted path and array index notation, combined). */
    public void testFIELDFunctionWithDotAndIndexNotation() throws Exception {
        ClientResponse cr;
        VoltTable result;
        Client client = getClient();
        loadJS1(client);

        cr = client.callProcedure("IdFieldProc", "inner.arr[0]", 0);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "inner.arr[1]", 2);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{2}});

        cr = client.callProcedure("IdFieldProc", "arr3d[2].veggies", "good for you");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{1},{2},{3}});

        cr = client.callProcedure("IdFieldProc", "arr3d[2].dairy", "3");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        validateTableOfLongs(result, new long[][]{{3}});
    }

    public void testARRAY_ELEMENTFunction() throws Exception {
        ClientResponse cr;
        VoltTable result;
        Client client = getClient();
        loadJS1(client);

        cr = client.callProcedure("IdArrayProc", "arr", 1, "1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(1L,result.getLong(0));

        try {
            cr = client.callProcedure("IdArrayProc", "arr", "NotNumeric", "1");
            fail("parameter check failed");
        }
        catch ( ProcCallException pcex) {
            assertTrue(pcex.getMessage().contains("TYPE ERROR FOR PARAMETER 1"));
        }

        try {
            cr = client.callProcedure("IdArrayProc", 1, 1, "1");
            assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        }
        catch ( ProcCallException pcex) {
            fail("parameter check failed");
        }

        cr = client.callProcedure("NullArrayProc", "funky", 2);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(9, result.getRowCount());

        cr = client.callProcedure("IdArrayProc", "id", 1, "1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(0, result.getRowCount());

        cr = client.callProcedure("@AdHoc", // test index out of bounds
                                  "SELECT ARRAY_ELEMENT(FIELD(DOC, 'arr'), 99) FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());

        cr = client.callProcedure("@AdHoc", // test negative index
                                  "SELECT ARRAY_ELEMENT(FIELD(DOC, 'arr'), -1) FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());

        cr = client.callProcedure("@AdHoc", // test scalar not an array
                                  "SELECT ARRAY_ELEMENT(FIELD(DOC, 'id'), 1) FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());

        cr = client.callProcedure("@AdHoc", // test object not an array
                                  "SELECT ARRAY_ELEMENT(FIELD(DOC, 'inner'), 1) FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());

        // Test top-level json array.
        cr = client.callProcedure("JS1.insert", 10, "[0, 10, 100]");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc",
                                  "SELECT ARRAY_ELEMENT(DOC, 1) FROM JS1 WHERE ID = 10");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals("10",result.getString(0));

        // Test empty json array.
        cr = client.callProcedure("JS1.insert", 11, "[]");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc",
                                  "SELECT ARRAY_ELEMENT(DOC, 0) FROM JS1 WHERE ID = 11");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getString(0);
        assertTrue(result.wasNull());
    }

    public void testARRAY_LENGTHFunction() throws Exception {
        ClientResponse cr;
        VoltTable result;
        Client client = getClient();
        loadJS1(client);

        cr = client.callProcedure("IdArrayLengthProc", "arr", 3);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(3, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(1L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(2L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(3L,result.getLong(0));

        try {
            cr = client.callProcedure("IdArrayLengthProc", "arr", "NoNumber");
            fail("parameter check failed");
        }
        catch ( ProcCallException pcex) {
            assertTrue(pcex.getMessage().contains("TYPE ERROR FOR PARAMETER 1"));
        }

        try {
            cr = client.callProcedure("IdArrayLengthProc", 1, 3);
            assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        }
        catch ( ProcCallException pcex) {
            fail("parameter check failed");
        }

        cr = client.callProcedure("NullFieldProc", "funky");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(9, result.getRowCount());

        cr = client.callProcedure("NullArrayLengthProc", "arr");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(6, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(4L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(5L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(6L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(7L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(8L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(9L,result.getLong(0));

        cr = client.callProcedure("LargeArrayLengthProc", "arr", 3);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(0, result.getRowCount());

        cr = client.callProcedure("LargeArrayLengthProc", "arr", 2);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(3, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(1L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(2L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(3L,result.getLong(0));

        cr = client.callProcedure("SmallArrayLengthProc", "arr", 2);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(0, result.getRowCount());

        cr = client.callProcedure("SmallArrayLengthProc", "arr", 3);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(3, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(1L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(2L,result.getLong(0));
        assertTrue(result.advanceRow());
        assertEquals(3L,result.getLong(0));

        cr = client.callProcedure("@AdHoc", // test scalar not an array
                                  "SELECT ARRAY_LENGTH(FIELD(DOC, 'id')) FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getLong(0);
        assertTrue(result.wasNull());

        cr = client.callProcedure("@AdHoc", // test object not an array
                                  "SELECT ARRAY_LENGTH(FIELD(DOC, 'inner')) FROM JS1 WHERE ID = 1");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        result.getLong(0);
        assertTrue(result.wasNull());

        // Test top-level json array.
        cr = client.callProcedure("JS1.insert", 10, "[0, 10, 100]");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc", // test object not an array
                                  "SELECT ARRAY_LENGTH(DOC) FROM JS1 WHERE ID = 10");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(3L,result.getLong(0));

        // Test empty json array.
        cr = client.callProcedure("JS1.insert", 11, "[]");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        cr = client.callProcedure("@AdHoc",
                                  "SELECT ARRAY_LENGTH(DOC) FROM JS1 WHERE ID = 11");
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());
        result = cr.getResults()[0];
        assertEquals(1, result.getRowCount());
        assertTrue(result.advanceRow());
        assertEquals(0L,result.getLong(0));
    }

    public void testFunctionsWithInvalidJSON() throws Exception {

        Client client = getClient();
        ClientResponse cr;

        cr = client.callProcedure(
                "JSBAD.insert", 1, // OOPS. skipped comma before "bool"
                "{\"id\":1 \"bool\": false}"
                );
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());

        cr = client.callProcedure(
                "JSBAD.insert", 2, // OOPS. semi-colon in place of colon before "bool"
                "{\"id\":2, \"bool\"; false, \"贾鑫Vo\":\"分かりません分かりません分かりません分かりません分かりません分かりません分かりません分かりません分かりません分かりません分かりません分かりません\"}"
                );
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());

        final String jsTrailingCommaArray = "[ 0, 100, ]"; // OOPS. trailing comma in array
        final String jsWithTrailingCommaArray = "{\"id\":3, \"trailer\":" + jsTrailingCommaArray + "}";

        cr = client.callProcedure("JSBAD.insert", 3, jsWithTrailingCommaArray);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());

        cr = client.callProcedure("JSBAD.insert", 4, jsTrailingCommaArray);
        assertEquals(ClientResponse.SUCCESS, cr.getStatus());


        String[] jsonProcs = { "BadIdFieldProc", "BadIdArrayProc", "BadIdArrayLengthProc" };

        for (String procname : jsonProcs) {
            try {
                cr = client.callProcedure(procname, 1, "id", "1");
                fail("document validity check failed for " + procname);
            }
            catch(ProcCallException pcex) {
                assertTrue(pcex.getMessage().contains("Invalid JSON * Line 1, Column 9"));
            }

            try {
                cr = client.callProcedure(procname, 2, "id", "2");
                fail("document validity check failed for " + procname);
            }
            catch(ProcCallException pcex) {
                assertTrue(pcex.getMessage().contains("Invalid JSON * Line 1, Column 16"));
            }

            try {
                cr = client.callProcedure(procname, 3, "id", "3");
                fail("document validity check failed for " + procname);
            }
            catch(ProcCallException pcex) {
                assertTrue(pcex.getMessage().contains("Invalid JSON * Line 1, Column 30"));
            }

            try {
                cr = client.callProcedure(procname, 4, "id", "4");
                fail("document validity check failed for " + procname);
            }
            catch(ProcCallException pcex) {
                assertTrue(pcex.getMessage().contains("Invalid JSON * Line 1, Column 11"));
            }
        }
    }

    //
    // JUnit / RegressionSuite boilerplate
    //
    public TestFunctionsForJSON(String name) {
        super(name);
    }

    static public junit.framework.Test suite() {

        VoltServerConfig config = null;
        MultiConfigSuiteBuilder builder =
            new MultiConfigSuiteBuilder(TestFunctionsForJSON.class);
        boolean success;

        VoltProjectBuilder project = new VoltProjectBuilder();
        final String literalSchema =
                "CREATE TABLE JS1 (\n" +
                "  ID INTEGER NOT NULL, \n" +
                "  DOC VARCHAR(8192),\n" +
                "  PRIMARY KEY(ID))\n" +
                ";\n" +

                "CREATE PROCEDURE IdFieldProc AS\n" +
                "   SELECT ID FROM JS1 WHERE FIELD(DOC, ?) = ? ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE InnerFieldProc AS\n" +
                "   SELECT ID FROM JS1 WHERE FIELD(FIELD(DOC, 'inner'), ?) = ? ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE NullFieldProc AS\n" +
                "   SELECT ID FROM JS1 WHERE FIELD(DOC, ?) IS NULL ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE IdArrayProc AS\n" +
                "   SELECT ID FROM JS1 WHERE ARRAY_ELEMENT(FIELD(DOC, ?), ?) = ? ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE NullArrayProc AS\n" +
                "   SELECT ID FROM JS1 WHERE ARRAY_ELEMENT(FIELD(DOC, ?), ?) IS NULL ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE IdArrayLengthProc AS\n" +
                "   SELECT ID FROM JS1 WHERE ARRAY_LENGTH(FIELD(DOC, ?)) = ? ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE NullArrayLengthProc AS\n" +
                "   SELECT ID FROM JS1 WHERE ARRAY_LENGTH(FIELD(DOC, ?)) IS NULL ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE SmallArrayLengthProc AS\n" +
                "   SELECT ID FROM JS1 WHERE ARRAY_LENGTH(FIELD(DOC, ?)) BETWEEN 0 AND ? ORDER BY ID\n" +
                ";\n" +
                "CREATE PROCEDURE LargeArrayLengthProc AS\n" +
                "   SELECT ID FROM JS1 WHERE ARRAY_LENGTH(FIELD(DOC, ?)) > ? ORDER BY ID\n" +
                ";\n" +

                "CREATE TABLE JSBAD (\n" +
                "  ID INTEGER NOT NULL,\n" +
                "  DOC VARCHAR(8192),\n" +
                "  PRIMARY KEY(ID))\n" +
                ";\n" +
                "CREATE PROCEDURE BadIdFieldProc AS\n" +
                "  SELECT ID FROM JSBAD WHERE ID = ? AND FIELD(DOC, ?) = ?\n" +
                ";\n" +
                "CREATE PROCEDURE BadIdArrayProc AS\n" +
                "  SELECT ID FROM JSBAD WHERE ID = ? AND ARRAY_ELEMENT(FIELD(DOC, ?), 1) = ?\n" +
                ";\n" +
                "CREATE PROCEDURE BadIdArrayLengthProc AS\n" +
                "  SELECT ID FROM JSBAD WHERE ID = ? AND ARRAY_LENGTH(FIELD(DOC, ?)) = ?\n" +
                ";\n" +
                "";
        try {
            project.addLiteralSchema(literalSchema);
        } catch (IOException e) {
            assertFalse(true);
        }

        // CONFIG #1: Local Site/Partition running on JNI backend
        config = new LocalCluster("fixedsql-onesite.jar", 1, 1, 0, BackendTarget.NATIVE_EE_JNI);
        success = config.compile(project);
        assertTrue(success);
        builder.addServerConfig(config);

        // CONFIG #2: Local Site/Partitions running on JNI backend
        config = new LocalCluster("fixedsql-threesite.jar", 3, 1, 0, BackendTarget.NATIVE_EE_JNI);
        success = config.compile(project);
        assertTrue(success);
        builder.addServerConfig(config);
/*

        // CONFIG #2: HSQL -- disabled, the functions being tested are not HSQL compatible
        config = new LocalCluster("fixedsql-hsql.jar", 1, 1, 0, BackendTarget.HSQLDB_BACKEND);
        success = config.compile(project);
        assertTrue(success);
        builder.addServerConfig(config);

*/
        // no clustering tests for functions

        return builder;
    }
}