package com.ttu.lunchbot.menuparser;

import com.ttu.lunchbot.model.Menu;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * MenuParser takes a PDF document containing menu texts, extracts the document
 * into a collection of lines of text and uses the given parsing strategy to parse
 * the texts into MenuItems and Menus that can later be used for easy data retrieval.
 */
public class MenuParser {

    /**
     * Object with methods for extracting text from PDF documents.
     */
    private PDFTextStripper stripper;

    /**
     * Strategy for parsing menu texts into MenuItems and Menus.
     */
    private MenuParserStrategy strategy;

    /**
     * Create a new MenuParser.
     * @param strategy Strategy for parsing menu texts into MenuItems and Menus.
     * @throws IOException When there is a problem with creating or configuring a PDFTextStripper.
     * @see PDFTextStripper
     */
    public MenuParser(MenuParserStrategy strategy) throws IOException {
        this.stripper = new PDFTextStripper();
        this.stripper.setSortByPosition(true);
        this.strategy = strategy;
    }

    /**
     * Extract lines of text from a PDF document and store them in a collection.
     * @param file File object representing a PDF document.
     * @return Collection of Strings extracted in top-to-bottom order from the given file.
     */
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

    /**
     * @param file File object representing a PDF document.
     * @return Collection of parsed Menus.
     * @see Menu
     */
    public ArrayList<Menu> parseMenus(File file) {
        return this.strategy.parse(this.extractLines(file));
    }

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/Charlie/Desktop/menu1.pdf");
        MenuParser menuParser = new MenuParser(new BalticRestaurantMenuParserStrategy());
        ArrayList<Menu> menus = menuParser.parseMenus(file);
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }
}
