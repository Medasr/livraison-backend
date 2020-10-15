package com.vinci.livraison.app.module.shared.schedule;

import com.vinci.livraison.app.module.commande.service.impl.CommandeService;
import com.vinci.livraison.app.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppSchedule {

    JwtService tokenService;
    CommandeService commandeService;

    @Scheduled(fixedDelay = 60000)
    public void clearDbFromRevokedAndExpiredToken(){
        tokenService.cleanDbFromRevokedAndExpiredTokens();
    }

    @Scheduled(fixedDelay = 60000)
    public void closeUnansweredOrders(){
        commandeService.closeUnansweredCommande();
    }



    /**
     *  <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year> <command>
     *  1:00AM :00 every day
     * */
    @Scheduled(cron = "0 0 1 * * ?")
    public void moveClosedOrdersToHistoryTable(){
        // TODO //
    }


}
