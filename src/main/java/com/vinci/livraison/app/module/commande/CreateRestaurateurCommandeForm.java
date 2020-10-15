package com.vinci.livraison.app.module.commande;

import com.vinci.livraison.app.module.commande.CreateCommandeForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurateurCommandeForm extends CreateCommandeForm {

    Long idClient;
}
