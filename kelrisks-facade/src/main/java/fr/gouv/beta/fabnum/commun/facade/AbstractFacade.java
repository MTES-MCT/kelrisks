package fr.gouv.beta.fabnum.commun.facade;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AbstractFacade {

}
