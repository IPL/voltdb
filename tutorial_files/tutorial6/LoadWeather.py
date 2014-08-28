import sys
from xml.dom.minidom import parseString
from voltdbclient import *

try:
	data = sys.stdin.read()
except Exception as e:
	print "ERROR: Failed to read data from stdin.\n", e
	exit()

try:
	xmldom = parseString(data)
except Exception as e:
	print "ERROR: Failed to parse input as XML.\n", e
	exit()

def getNodeValue(dom,id):
      return dom.getElementsByTagName(id)[0].firstChild.nodeValue

def getElement(node,tag):
      if (node.nodeType == node.ELEMENT_NODE):
          if (node.tagName == tag):
                return node.firstChild.nodeValue
      return ""
 
client = FastSerializer("localhost", 21212)

finder = VoltProcedure( client, "FindAlert", [FastSerializer.VOLTTYPE_STRING, 
] )

loader = VoltProcedure( client, "LoadAlert", [FastSerializer.VOLTTYPE_STRING, 
FastSerializer.VOLTTYPE_STRING, 
FastSerializer.VOLTTYPE_STRING, 
FastSerializer.VOLTTYPE_STRING, 
FastSerializer.VOLTTYPE_STRING, 
FastSerializer.VOLTTYPE_STRING,
FastSerializer.VOLTTYPE_STRING,
FastSerializer.VOLTTYPE_STRING
] )

alerts = xmldom.getElementsByTagName('entry')

cOld = 0
cLoaded = 0


for w in alerts:
     id = getNodeValue(w,'id')
     updated = getNodeValue(w,'updated')
     summary = getNodeValue(w,'summary')
     wtype = getNodeValue(w,'cap:event')
     starttime = getNodeValue(w,'cap:effective')
     endtime = getNodeValue(w,'cap:expires')
     severity = getNodeValue(w,'cap:severity')
     geocode = w.getElementsByTagName('cap:geocode')
     fips = ""
     child = geocode[0].firstChild
     while (fips == "" and child):
          if (getElement(child,"valueName") == "FIPS6"):
               value = child.nextSibling
               while (value and getElement(value,"value") == ""):
                    value = value.nextSibling
               fips =  getElement(value,"value")
          child = child.nextSibling

     # Check to see if the alert is already in the database.
     response = finder.call([ id ])
     if (response.tables):
         if (response.tables[0].tuples):
               # Existing alert
               cOld += 1
         else:
               # New alert
               response = loader.call([ id, wtype, severity, summary, 
                                        starttime, endtime, updated, fips]) 
               if response.status == 1:
                     cLoaded += 1    
 
print cLoaded, " new alerts loaded."
print cOld, " existing alerts ignored."

client.close()


