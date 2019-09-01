package net.in.rrrekin.varia.rest


import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import spock.lang.Shared
import spock.lang.Specification
/**
 * @author michal.rudewicz@gmail.com
 */
class OkHttp3Test extends Specification {

    static final baseUrl = 'https://jsonplaceholder.typicode.com'
    static final postBody = [string: 'txt', number: 42, list: ['abc', 'def', 'xyz'], map: [a: 3, b: 7, c: 42]]
    @Shared OkHttpClient client


    void setupSpec() {

        def logging = new HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        client = new OkHttpClient.Builder().addInterceptor(logging).build()
    }

    def "get request"() {
        expect:
        HttpUrl url = HttpUrl.parse(baseUrl).newBuilder()
                .addPathSegments('posts/1')
                .addQueryParameter("v", "1.0")
                .addQueryParameter("user", "vogella")
                .build()

        Request request = new Request.Builder()
                .url(url)
                .addHeader('Accept', 'application/json')
                .build();

        Call call = client.newCall(request)
        println call
        Response response = call.execute()
        println response
    }

    def "post request form"() {
        expect:
        RequestBody formBody = new FormBody.Builder()
                .add("string", "txt[=]")
                .add("number", "42")
                .addEncoded('strenc', '"txt[=]"')
                .build()

        Request request = new Request.Builder()
                .url(baseUrl + "/posts")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        println response
    }

    def "post request json"() {
        expect:
        RequestBody formBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), '{"id":1,"name":"John"}');

        Request request = new Request.Builder()
                .url(baseUrl + "/posts")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        println response
    }
}
