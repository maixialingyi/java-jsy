package com.jsy.learn.io.nettyRpc0;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Packmsg {

    Myheader header;
    MyContent content;

    public Packmsg(Myheader header, MyContent content) {
        this.header = header;
        this.content = content;
    }
}
