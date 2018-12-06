package fr.gouv.beta.fabnum.commun.metier.impl;


import fr.gouv.beta.fabnum.commun.metier.IAbstractService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe abstraite ancêtre de tous les services <p>
 * Date de re-création : 12 sept. 07
 *
 * @author alain
 */
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractService implements IAbstractService {
    
    private static final long serialVersionUID = -8335321138832324958L;
}
