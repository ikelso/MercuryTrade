package com.mercury.platform.ui.frame.movable.container;

import com.mercury.platform.shared.config.Configuration;
import com.mercury.platform.shared.config.configration.PlainConfigurationService;
import com.mercury.platform.shared.config.descriptor.NotificationSettingsDescriptor;
import com.mercury.platform.shared.entity.message.FlowDirections;
import com.mercury.platform.shared.store.MercuryStoreCore;
import com.mercury.platform.ui.components.ComponentsFactory;
import com.mercury.platform.ui.components.panel.VerticalScrollContainer;
import com.mercury.platform.ui.components.panel.notification.NotificationPanel;
import com.mercury.platform.ui.components.panel.notification.factory.NotificationPanelFactory;
import com.mercury.platform.ui.frame.movable.AbstractMovableComponentFrame;
import com.mercury.platform.ui.misc.AppThemeColor;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class NotificationFrame extends AbstractMovableComponentFrame {
    private List<NotificationPanel> notificationPanels;
    private PlainConfigurationService<NotificationSettingsDescriptor> config;
    private NotificationPanelFactory factory;
    private JPanel container;
    @Override
    protected void initialize() {
        super.initialize();
        this.processSEResize = false;
        this.notificationPanels = new ArrayList<>();
        this.config = Configuration.get().notificationConfiguration();
        this.componentsFactory.setScale(this.scaleConfig.get("notification"));
        this.stubComponentsFactory.setScale(this.scaleConfig.get("notification"));
        this.factory = new NotificationPanelFactory();
    }

    @Override
    public void onViewInit() {
        this.getRootPane().setBorder(null);
        this.setBackground(AppThemeColor.TRANSPARENT);
        this.container = new VerticalScrollContainer();
        this.container.setBackground(AppThemeColor.TRANSPARENT);
        this.container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        this.add(this.getExpandPanel(),BorderLayout.LINE_START);
        this.add(this.container,BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }

    @Override
    @SuppressWarnings("all")
    public void subscribe() {
        MercuryStoreCore.newNotificationSubject.subscribe(notification -> {
            NotificationPanel notificationPanel = this.factory.getProviderFor(notification.getType())
                    .setData(notification)
                    .setComponentsFactory(this.componentsFactory)
                    .build();
            this.notificationPanels.add(notificationPanel);
            this.container.add(notificationPanel);
            this.pack();
            this.repaint();
        });
        MercuryStoreCore.removeNotificationSubject.subscribe(notification -> {
            NotificationPanel notificationPanel = this.notificationPanels.stream()
                    .filter(it -> it.getData().equals(notification))
                    .findAny().orElse(null);
            notificationPanel.onViewDestroy();
            this.container.remove(notificationPanel);
            this.notificationPanels.remove(notificationPanel);
            this.pack();
            this.repaint();
        });
    }

    @Override
    protected JPanel getPanelForPINSettings() {
        return new JPanel();
    }

    @Override
    protected void registerDirectScaleHandler() {
    }

    @Override
    protected void performScaling(Map<String, Float> scaleData) {
    }
    @Override
    protected JPanel defaultView(ComponentsFactory factory) {
        return null;
    }

    @Override
    protected LayoutManager getFrameLayout() {
        return new BorderLayout();
    }

    private JPanel getExpandPanel(){
        JPanel root = this.componentsFactory.getJPanel(new BorderLayout());
        root.setBackground(AppThemeColor.MSG_HEADER);
        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1,1,1,0,AppThemeColor.FRAME),
                BorderFactory.createMatteBorder(1,1,1,1,AppThemeColor.RESPONSE_BUTTON_BORDER)));

        JPanel labelPanel = componentsFactory.getTransparentPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(AppThemeColor.MSG_HEADER);
        labelPanel.setPreferredSize(new Dimension((int)(10 * componentsFactory.getScale()),(int)(22 * componentsFactory.getScale())));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(-4,0,0,0));
        JLabel msgCountLabel = componentsFactory.getTextLabel("+" + String.valueOf("12"));
        String iconPath = "app/collapse-all.png";
        JButton expandButton = componentsFactory.getIconButton(iconPath,22,AppThemeColor.MSG_HEADER,"");
        expandButton.addActionListener(action -> {

        });
        expandButton.setAlignmentY(SwingConstants.CENTER);
        labelPanel.add(msgCountLabel);
        root.add(expandButton,BorderLayout.CENTER);
        root.add(labelPanel,BorderLayout.PAGE_END);
        return root;
    }
}
