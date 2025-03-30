package com.bps.plantseeds3.common.config;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0003\t\n\u000bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/bps/plantseeds3/common/config/AppConfig;", "", "()V", "APP_NAME", "", "APP_VERSION", "DATABASE_NAME", "DATABASE_VERSION", "", "Api", "UI", "Validation", "common_debug"})
public final class AppConfig {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_NAME = "PlantSeeds3";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String APP_VERSION = "1.0.0";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "plantseeds3.db";
    public static final int DATABASE_VERSION = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.bps.plantseeds3.common.config.AppConfig INSTANCE = null;
    
    private AppConfig() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/bps/plantseeds3/common/config/AppConfig$Api;", "", "()V", "BASE_URL", "", "TIMEOUT", "", "common_debug"})
    public static final class Api {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String BASE_URL = "https://api.plantseeds3.com/";
        public static final long TIMEOUT = 30L;
        @org.jetbrains.annotations.NotNull()
        public static final com.bps.plantseeds3.common.config.AppConfig.Api INSTANCE = null;
        
        private Api() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/bps/plantseeds3/common/config/AppConfig$UI;", "", "()V", "ANIMATION_DURATION", "", "DEFAULT_PADDING", "", "DEFAULT_SPACING", "common_debug"})
    public static final class UI {
        public static final long ANIMATION_DURATION = 300L;
        public static final int DEFAULT_PADDING = 16;
        public static final int DEFAULT_SPACING = 8;
        @org.jetbrains.annotations.NotNull()
        public static final com.bps.plantseeds3.common.config.AppConfig.UI INSTANCE = null;
        
        private UI() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/bps/plantseeds3/common/config/AppConfig$Validation;", "", "()V", "MAX_DESCRIPTION_LENGTH", "", "MAX_NAME_LENGTH", "MIN_NAME_LENGTH", "MIN_PASSWORD_LENGTH", "common_debug"})
    public static final class Validation {
        public static final int MIN_NAME_LENGTH = 2;
        public static final int MAX_NAME_LENGTH = 50;
        public static final int MAX_DESCRIPTION_LENGTH = 500;
        public static final int MIN_PASSWORD_LENGTH = 6;
        @org.jetbrains.annotations.NotNull()
        public static final com.bps.plantseeds3.common.config.AppConfig.Validation INSTANCE = null;
        
        private Validation() {
            super();
        }
    }
}