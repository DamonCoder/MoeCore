package ltd.maimeng.core.cache;

import android.content.Context;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CacheLoader {

    public synchronized static void loadAllCacheDirs(Context context) throws IOException, ParserConfigurationException, SAXException {
        ltd.maimeng.core.cache.CacheStorage.getInstance().clear();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        ParserHandler parserHandler = new ParserHandler();
        InputStream inputStream = context.getAssets().open("tea_cache.xml");
        parser.parse(inputStream, parserHandler);
        inputStream.close();
    }

    private static class ParserHandler extends DefaultHandler {

        private CacheDir cacheDir;

        public ParserHandler() {
        }

        @Override
        public void startDocument() throws SAXException {
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if (localName.equals("SDCardCache")) {
                String name = attributes.getValue("name");
                String author = attributes.getValue("author");
                String feature = attributes.getValue("feature");
                String desc = attributes.getValue("desc");
                String dir = attributes.getValue("dir");

                cacheDir = new CacheDir();
                cacheDir.setName(name);
                cacheDir.setAuthor(author);
                cacheDir.setFeature(feature);
                cacheDir.setDesc(desc);
                cacheDir.setRelativePath(dir);

                try {
                    ltd.maimeng.core.cache.CacheManager.getInstance().checkCacheDir(cacheDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
