package net.in.rrrekin.varia.rest

import spock.lang.Specification
import wslite.rest.ContentType
import wslite.rest.RESTClient
import wslite.rest.RESTClientException
/**
 *
 * https://github.com/jwagenleitner/groovy-wslite
 *
 *
 * @author michal.rudewicz@gmail.com
 */
class RestClientTest extends Specification {

    final static postsClient = new RESTClient('https://jsonplaceholder.typicode.com/posts')
    final static localClient = new RESTClient('http://localhost')

    def "get request"() {
        expect:
        def response = postsClient.get(path: '/1',
                accept: ContentType.JSON,
                query: [screen_name: 'jwagenleitner', include_entities: true],
//                headers: ["X-Foo": "bar"],
//                connectTimeout: 5000,
//                readTimeout: 10000,
//                followRedirects: false,
//                useCaches: false,
//                sslTrustAllCerts: true
        )

        println response
        println response.statusCode
        println response.statusMessage
        println response.headers
        println response.contentType
        println response.contentAsString
    }

    def "get request 2"() {
        when:
        def response = localClient.get(path: '/1',
                accept: ContentType.JSON,
        )
        then:
        def ex = thrown RESTClientException

        println ex.response
        println ex.response.statusCode
        println ex.response.headers
        println ex.response.statusMessage
        println ex.response.contentType
        println ex.response.contentAsString
    }

    def "post request form"() {
        when:
        def response = localClient.post(path: '/1',
                accept: ContentType.JSON) {
            type ContentType.URLENC
            urlenc string: 'txt', number: 42, list: ['abc', 'def', 'xyz'], map: [a: 3, b: 7, c: 42]

        }
        then:
        def ex = thrown RESTClientException

        println ex.response
        println ex.response.statusCode
        println ex.response.headers
        println ex.response.statusMessage
        println ex.response.contentType
        println ex.response.contentAsString
    }

}
