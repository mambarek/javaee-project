package com.it2go.employee.ui.listener;

import com.it2go.employee.entities.EntityNotValidException;
import com.it2go.employee.ui.controller.EditEmployeeController;
import com.it2go.framework.dao.BaseException;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityRemovedException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class EditEmployeeEditorActionListener implements ActionListener, Serializable {

    @Inject
    EditEmployeeController editEmployeeController;

    final static String SAVE_BUTTON_ID = "saveOk";
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {

        final UIComponent component = event.getComponent();
        final String componentId = component.getId();
        if(componentId.equals(SAVE_BUTTON_ID)){
            try {
                editEmployeeController.saveEmployee();
            } catch (Exception e) {

                FacesContext.getCurrentInstance().addMessage(event.getComponent().getParent().getClientId(),
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Details für dieses Fehler"));

                e.printStackTrace();
            }

        }
    }
}
