package net.in.rrrekin.varia.rest

import groovy.util.logging.Commons
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import spock.lang.Specification

/**
 * @author michal.rudewicz@gmail.com
 *
 * https://github.com/jgritman/httpbuilder/wiki/RESTClient
 */
@Commons
class HttpBuilderTest extends Specification {


    def "get request0"() {
        expect:
        log.info "Dupa"
//        System.getProperties().each {
//            println "$it.key -> $it.value"
//        }
        def restClient = new RESTClient("https://jsonplaceholder.typicode.com")
        HttpResponseDecorator response = restClient.get(path: '/posts/1', query: ['city': 'Prague'])
        println "Status     : ${response.status}"
        println "Body       : ${response.data}"

    }

    def "xxx"() {
        expect:
        // GET
        def get = new URL("https://httpbin.org/get").openConnection();
        def getRC = get.getResponseCode();
        println(getRC);
        if(getRC.equals(200)) {
            println(get.getInputStream().getText());
        }

// POST
        def post = new URL("https://httpbin.org/post").openConnection();
        def message = '{"message":"this is a message"}'
        post.setRequestMethod("POST")
        post.setDoOutput(true)
        post.setRequestProperty("Content-Type", "application/json")
        post.getOutputStream().write(message.getBytes("UTF-8"));
        def postRC = post.getResponseCode();
        println(postRC);
        if(postRC.equals(200)) {
            println(post.getInputStream().getText());
        }
    }
}
