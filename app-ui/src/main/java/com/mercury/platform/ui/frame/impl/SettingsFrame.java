package com.mercury.platform.ui.frame.impl;


import com.mercury.platform.shared.events.EventRouter;
import com.mercury.platform.shared.events.custom.CloseFrameEvent;
import com.mercury.platform.shared.events.custom.DraggedWindowEvent;
import com.mercury.platform.shared.events.custom.RepaintEvent;
import com.mercury.platform.ui.components.panel.SettingsPanel;
import com.mercury.platform.ui.frame.OverlaidFrame;
import com.mercury.platform.ui.misc.AppThemeColor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Константин on 16.12.2016.
 */
public class SettingsFrame extends OverlaidFrame {
    public SettingsFrame(){
        super("MT-Settings");
        setFocusableWindowState(true);
        setFocusable(true);

        SettingsPanel settingsPanel = new SettingsPanel();
        this.add(settingsPanel);
        this.getRootPane().setBorder(BorderFactory.createLineBorder(AppThemeColor.BORDER,1));
        pack();
        disableHideEffect();
    }

    @Override
    public void initHandlers() {
        EventRouter.registerHandler(CloseFrameEvent.class, event -> {
            EventRouter.clear(CloseFrameEvent.class);
            SettingsFrame.this.setVisible(false);
            SettingsFrame.this.dispose();
        });
        EventRouter.registerHandler(RepaintEvent.class, event -> {
            EventRouter.clear(RepaintEvent.class);
            SettingsFrame.this.revalidate();
            SettingsFrame.this.repaint();
        });
        EventRouter.registerHandler(DraggedWindowEvent.class, event -> {
            int x = ((DraggedWindowEvent) event).getX();
            int y = ((DraggedWindowEvent) event).getY();
            SettingsFrame.this.setLocation(x,y);
            configManager.saveFrameLocation(this.getClass().getSimpleName(),SettingsFrame.this.getLocation());
        });
    }

    @Override
    protected LayoutManager getFrameLayout() {
        return new FlowLayout();
    }
}