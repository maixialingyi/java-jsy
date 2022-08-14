package com.jdd.sznp.config.aspect;
import lombok.Data;

@Data
public class AppContext {
    private static final InheritableThreadLocal<AppContext> HOLDER = new InheritableThreadLocal<>();

    private Integer groupId;

    public static void remove() {
        HOLDER.remove();
    }

    public static void setAppContext(AppContext appContext) {
        HOLDER.set(appContext);
    }

    public static AppContext getAppContext() {
        return HOLDER.get();
    }

}
