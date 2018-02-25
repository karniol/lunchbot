package com.ttu.lunchbot.menuparser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MenuParser {

    private PDFTextStripper stripper;
    private MenuParserStrategy strategy;

    private MenuParser(MenuParserStrategy strategy) throws IOException {
        this.stripper = new PDFTextStripper();
        this.stripper.setSortByPosition(true);
        this.strategy = strategy;
    }

    private ArrayList<String> extractLines(File file) {
        ArrayList<String> lines = new ArrayList<>();

        try (PDDocument document = PDDocument.load(file)) {
            String text = this.stripper.getText(document);
            lines.addAll(Arrays.asList(text.split("\n")));
            lines.replaceAll(String::trim);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    Menu parseMenu(File file) {
        return this.strategy.parse(this.extractLines(file));
    }

    public static void main(String[] args) throws IOException {
        MenuParser menuParser = new MenuParser(new DailyMenuParserStrategy());
        menuParser.parseMenu(new File("/Users/Charlie/Desktop/menu3.pdf"));
    }
}
