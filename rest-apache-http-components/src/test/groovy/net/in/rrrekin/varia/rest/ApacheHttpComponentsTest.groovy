package net.in.rrrekin.varia.rest

import groovy.json.JsonOutput
import org.apache.hc.client5.http.HttpResponseException
import org.apache.hc.client5.http.fluent.Form
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.io.entity.EntityUtils
import spock.lang.Specification

import java.nio.charset.StandardCharsets

/**
 * @author michal.rudewicz@gmail.com
 */
class ApacheHttpComponentsTest extends Specification {

    static final postBody = [string: 'txt', number: 42, list: ['abc', 'def', 'xyz'], map: [a: 3, b: 7, c: 42]]


    def "get request" () {
        expect:
        def request = Request.Get('https://jsonplaceholder.typicode.com/posts/2').execute()
        def response = request.returnContent()
        println response.type.mimeType
        println response.asString()
        println JsonOutput.prettyPrint(response.asString())
    }

    def "get request 2" () {
        when:
        def request = Request.Get('https://jsonplaceholder.typicode.com/posts/20000000000').execute()
        def response = request.returnContent()
        then:
        def ex = thrown  HttpResponseException
        println ex
    }

    def "get request - response" () {
        when:
        def request = Request.Get('https://jsonplaceholder.typicode.com/posts/20000000000').execute()
        CloseableHttpResponse response = request.returnResponse()
        then:
        println response
        println response.entity

    }

    def "get request 3" () {
        when:
        def request = Request.Get('http://localhost/posts/2').execute()
        def response = request.returnContent()

        then:
        def ex = thrown  HttpResponseException
        println ex
    }

    def "post request form"() {
        expect:
        CloseableHttpResponse response = Request.Post("http://localhost/login")
                .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
                .execute().returnResponse()
        println response.code
        println response.reasonPhrase
        println EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
    }

    def "post request json"() {
        expect:
        CloseableHttpResponse response = Request.Post("http://localhost/login")
                .bodyString(JsonOutput.toJson(postBody), ContentType.APPLICATION_JSON)
                .execute().returnResponse()
        println response.code
        println response.reasonPhrase
        println EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8)
    }
}
