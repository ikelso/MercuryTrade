package com.mercury.platform.ui.components.panel.notification.controller;


public interface OutgoingPanelController {
    void visitHideout();
    void backToHideout();
    void performTrade();
    void performLeave();
    void performHide();
    void performOpenChat();
    void performResponse(String response);
}
