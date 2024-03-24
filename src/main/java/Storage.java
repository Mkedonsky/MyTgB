import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Storage {
    ArrayList<String> quoteList = new ArrayList<>();

    Storage() {
//        quoteList.add("Начинать всегда стоит с того, что сеет сомнения. \n\nБорис Стругацкий.");
//        quoteList.add("80% успеха - это появиться в нужном месте в нужное время.\n\nВуди Аллен");
//        quoteList.add("Мы должны признать очевидное: понимают лишь те,кто хочет понять.\n\nБернар Вербер");
        parser("https://citatnica.ru/citaty/mudrye-tsitaty-velikih-lyudej");
    }

    public String getRandQuote() {
        int randomIndex = (int) (Math.random() * quoteList.size());
        return quoteList.get(randomIndex);
    }

    public void parser(String srtUrl) {

        String className = "su-note-inner su-u-clearfix su-u-trim";
        Document doc = null;
        try {
            doc = Jsoup.connect(srtUrl).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Elements elements = doc.getElementsByClass(className);
            elements.forEach(element -> quoteList.add(element.text()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}
