package com.mercury.platform.shared.config.descriptor.adr;


import com.mercury.platform.shared.config.descriptor.HotKeyDescriptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdrComponentDescriptor implements Serializable{
    private int id;
    private String title = "default";
    private AdrComponentType type;
    private Point location;
    private Dimension size;
    private HotKeyDescriptor hotKeyDescriptor;
    private boolean visible = true;
    private float scale = 1f;
    private float opacity = 1f;
    private AdrComponentOrientation orientation;
}
