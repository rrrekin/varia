package net.in.rrrekin.varia.rest

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author michal.rudewicz@gmail.com
 */
class RestTemplateTest extends Specification {

    static final postBody = [string: 'txt', number: 42, list: ['abc', 'def', 'xyz'], map: [a: 3, b: 7, c: 42]]

    @Shared
            restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory())

    def "get request"() {
        expect:
        def response = restTemplate.getForObject(
                'https://jsonplaceholder.typicode.com/posts/1', Object.class);

        println response
    }

    def "get request 2"() {
        when:
        def response = restTemplate.getForObject(
                'https://jsonplaceholder.typicode.com/posts/20000000000', Object.class);
        println response
        then:
        def ex = thrown HttpClientErrorException.NotFound
        println ex
        println ex.message
        println ex.statusCode
        println ex.responseBodyAsString
    }

    def "get request 3"() {
        when:
        def response = restTemplate.getForObject('http://localhost/posts/2', Object.class)
        println response

        then:
        def ex = thrown HttpClientErrorException.NotFound
        println ex
        println ex.message
        println ex.statusCode
        println ex.responseBodyAsString
    }

    def "get request entity 3"() {
        when:
        def response = restTemplate.getForEntity('http://localhost/posts/2', Object.class)
        println response

        then:
        def ex = thrown HttpClientErrorException.NotFound
        println ex
        println ex.message
        println ex.statusCode
        println ex.responseBodyAsString
    }


    def "post request form"() {
        given:
        // "email=first.last%40example.com&number=42&list=%5Babc%2C+def%2C+xyz%5D&map=%7Ba%3D3%2C+b%3D7%2C+c%3D42%7D"
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>()
        map.add("email", "first.last@example.com")
        map.add("number", 42)
        map.add("list", ['abc', 'def', 'xyz'])
        map.add("map",  [a: 3, b: 7, c: 42])

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers)

        when:
        def response = restTemplate.postForObject('http://localhost/login', request, Object.class)
        println response

        then:
        thrown Exception
    }

    def "post request form 2"() {
        given:
        // list=abc&list=def&list=xyz
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>()
        map.addAll("list", ['abc', 'def', 'xyz'])

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers)

        when:
        def response = restTemplate.postForObject('http://localhost/login', request, Object.class)
        println response

        then:
        thrown Exception
    }

    def "post request json"() {
        when:
        def response = restTemplate.postForObject('http://localhost/login', postBody, Object.class)
        println response

        then:
        thrown Exception
    }

}
