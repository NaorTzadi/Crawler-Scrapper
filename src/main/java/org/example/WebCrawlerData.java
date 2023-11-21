package org.example;

import java.util.ArrayList;

public record WebCrawlerData(int iterationsCount, int uniqueLinksCount, ArrayList<String> linksList) {
}
