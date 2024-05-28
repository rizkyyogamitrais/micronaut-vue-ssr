package au.com.clubconnect.service

import groovy.util.logging.Slf4j
import groovy.transform.CompileStatic
import jakarta.inject.Singleton

@Slf4j
@Singleton
@CompileStatic
class WordService {
    Map<String, String> getWord(String pageId) {

        Map<String, String> map = [:]

        if (pageId.equals("firstPage")) {
            map.put("word", "first page data")
        } else {
            map.put("word", "second page data")
        }
        return map
    }
}