package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.converter.CalendarConverter;
import com.ttu.lunchbot.spring.models.Cafe;
import com.ttu.lunchbot.spring.models.Menu;
import com.ttu.lunchbot.spring.repositories.CafeRepository;
import com.ttu.lunchbot.spring.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CafeService {

    private CafeRepository cafeRepository;

    private MenuRepository menuRepository;

    private ParseService parseService;

    public CafeService(CafeRepository cafeRepository, MenuRepository menuRepository, ParseService parseService) {
        this.cafeRepository = cafeRepository;
        this.menuRepository = menuRepository;
        this.parseService = parseService;
    }

    public Cafe getCafeById(long cafeId) {
        return cafeRepository.findOne(cafeId);
    }

    public List<Cafe> getAllCafes() {
        return cafeRepository.findAll();
    }

    public Cafe addCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public Menu getMenuOfToday(long cafeId) {
        List<Menu> menuList = menuRepository.findByCafe_Id(cafeId);
        if (menuList.size() == 0
            || menuList.get(menuList.size() - 1).getDate().isBefore(LocalDate.now())) {
            menuList.addAll(parseService.parseCafeMenu(cafeId));
        }
        CalendarConverter calendarConverter = new CalendarConverter();

        Menu leastDaysDifferentFromNowMenu = null;
        for (Menu menu : menuList) {
            if (menu.getDate() == null) {
                System.out.println(menu.getName() + " has no date!");
                continue;
            }
            if (menu.getDate().equals(LocalDate.now())) return menu;

            // If we can't find a menu for today, then find the menu with the date closest to today.
            if (leastDaysDifferentFromNowMenu == null
                || calendarConverter.getAbsDaysFromNow(leastDaysDifferentFromNowMenu)
                   > calendarConverter.getAbsDaysFromNow(menu)) {
                leastDaysDifferentFromNowMenu = menu;
            }
        }

        return leastDaysDifferentFromNowMenu;
    }

}
