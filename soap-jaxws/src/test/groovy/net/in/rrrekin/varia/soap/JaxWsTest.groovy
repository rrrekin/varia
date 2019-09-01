package net.in.rrrekin.varia.soap


import org.tempuri.SOAPDemo
import org.tempuri.SOAPDemoSoap
import spock.lang.Specification

import javax.xml.ws.Service
/**
 * @author michal.rudewicz@gmail.com
 */
class JaxWsTest extends Specification{

    def "sample operation"() {
        expect:

        URL url = new URL("http://www.crcind.com/csp/samples/SOAP.Demo.cls?wsdl");

        Service service = new SOAPDemo(url)
        SOAPDemoSoap port = service.SOAPDemoSoap

        def response = port.findPerson('1') //getByName('test name')
        println response
        println response.age

    }


}

