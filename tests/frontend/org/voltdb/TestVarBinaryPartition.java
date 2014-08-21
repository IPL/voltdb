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

package org.voltdb;

import java.util.Random;

import junit.framework.TestCase;

import org.voltdb.VoltDB.Configuration;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.compiler.VoltProjectBuilder;
import org.voltdb.utils.Encoder;
import org.voltdb.utils.MiscUtils;

public class TestVarBinaryPartition extends TestCase {

    private String pathToCatalog;
    private String pathToDeployment;
    private ServerThread localServer;
    private VoltDB.Configuration config;
    private VoltProjectBuilder builder;
    private Client client;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }


    public void testPartitionAndInsert () throws Exception {
        String my_schema =
                "create table BLAH (" +
                "clm_varinary varbinary(128) default '00' not null," +
                "clm_smallint smallint default 0 not null, " +
                ");\n" +
                "";

            pathToCatalog = Configuration.getPathToCatalogForTest("csv.jar");
            pathToDeployment = Configuration.getPathToCatalogForTest("csv.xml");
            builder = new VoltProjectBuilder();

            builder.addLiteralSchema(my_schema);
            builder.addPartitionInfo("BLAH", "clm_varinary");
            //builder.addStmtProcedure("Insert", "INSERT into BLAH values (?, ?, ?, ?, ?, ?, ?);");
            boolean success = builder.compile(pathToCatalog, 2, 1, 0);
            assertTrue(success);
            MiscUtils.copyFile(builder.getPathToDeployment(), pathToDeployment);
            config = new VoltDB.Configuration();
            config.m_pathToCatalog = pathToCatalog;
            config.m_pathToDeployment = pathToDeployment;
            localServer = new ServerThread(config);
            client = null;

            localServer.start();
            localServer.waitForInitialization();

            client = ClientFactory.createClient();
            client.createConnection("localhost");

        ClientResponse resp = client.callProcedure("@AdHoc", "INSERT INTO BLAH VALUES ('22',1);" );
        assertTrue( resp.getResults()[0].asScalarLong() == 1 );
        resp = client.callProcedure("@AdHoc", "INSERT INTO BLAH VALUES ('80',3);" );
        assertTrue( resp.getResults()[0].asScalarLong() == 1 );
        resp = client.callProcedure("@AdHoc", "INSERT INTO BLAH VALUES ('8081828384858687888990',4);" );
        assertTrue( resp.getResults()[0].asScalarLong() == 1 );

        Random rand = new Random();
        for( int i = 0; i < 1000; i++ ){
            byte[] bytes = new byte[rand.nextInt(128)];
            rand.nextBytes(bytes);
            String hexString = Encoder.hexEncode(bytes);
            // Just to mix things up a little, alternate the form of the parameter passing among
            // a literal hex string, a hex string parameter, and a byte[] parameter and independently
            // validate the insert using another form in the SELECT.
            if ( i % 2 == 0 ) {
                if ( i % 4 == 0 ) {
                    resp = client.callProcedure("@AdHoc",
                            "INSERT INTO BLAH VALUES ('" + hexString + "',5);" );
                } else {
                    resp = client.callProcedure("@AdHoc",
                            "INSERT INTO BLAH VALUES (?,5);", hexString);
                }
            } else {
                resp =  client.callProcedure("@AdHoc",
                            "INSERT INTO BLAH VALUES (?,5);", bytes);
            }
            assertTrue( resp.getResults()[0].asScalarLong() == 1 );

            int j = i / 4;
            if ( j % 2 == 0 ) {
                if ( j % 4 == 0 ) {
                    resp = client.callProcedure("@AdHoc",
                            "SELECT COUNT(*) FROM BLAH WHERE clm_varinary = CAST('" + hexString +
                            "' AS VARBINARY);");
                } else {
                    resp = client.callProcedure("@AdHoc",
                            "SELECT COUNT(*) FROM BLAH WHERE clm_varinary = ?;", hexString);
                }
            } else {
                resp = client.callProcedure("@AdHoc",
                        "SELECT COUNT(*) FROM BLAH WHERE clm_varinary = ?;", bytes);
            }
            assertTrue( resp.getResults()[0].asScalarLong() >= 1 );
        }
    }
}
