package com.ttu.lunchbot.spring.services;

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

    public Cafe addCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public Cafe getCafeById(long cafeId) {
        return cafeRepository.findOne(cafeId);
    }

    public List<Cafe> getAllCafes() {
        return cafeRepository.findAll();
    }

    public Menu getMenuOfToday(long cafeId) {
        List<Menu> menuList = menuRepository.findByCafe_Id(cafeId);
        System.out.println(menuList.size());
        if (menuList.size() == 0
            || menuList.get(menuList.size() - 1).getDate().toLocalDate().isBefore(LocalDate.now())) {
            menuList.addAll(parseService.parseCafeMenu(cafeId));
        }

        Menu leastDaysDifferentFromNowMenu = null;
        for (Menu menu : menuList) {
            if (menu.getDate() == null) {
                System.out.println(menu.getName() + " has no date!");
                continue;
            }
            if (menu.getDate().toLocalDate().equals(LocalDate.now())) return menu;

            if (leastDaysDifferentFromNowMenu == null
                || getAbsDaysFromNow(leastDaysDifferentFromNowMenu) > getAbsDaysFromNow(menu)) {
                leastDaysDifferentFromNowMenu = menu;
            }
        }

        return leastDaysDifferentFromNowMenu;
    }

    private long getAbsDaysFromNow(Menu menu) {
        return Math.abs(ChronoUnit.DAYS.between(menu.getDate().toLocalDate(), LocalDate.now()));
    }

}
