package com.igniva.qwer.model;

public class ClientProfile {
        private String name;
        private boolean allowOutgoing = true;
        private boolean allowIncoming = true;


        public ClientProfile(String name, boolean allowOutgoing, boolean allowIncoming) {
            this.name = name;
            this.allowOutgoing = allowOutgoing;
            this.allowIncoming = allowIncoming;
        }

        public String getName() {
            return name;
        }

        public boolean isAllowOutgoing() {
            return allowOutgoing;
        }

        public boolean isAllowIncoming() {
            return allowIncoming;
        }
    }
