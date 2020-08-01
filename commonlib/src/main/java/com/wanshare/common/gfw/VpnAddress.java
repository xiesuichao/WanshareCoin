package com.wanshare.common.gfw;

import java.util.List;

public class VpnAddress {

    /**
     * version : 0.0.1
     * hosts : [["192.168.90.23",30001],["192.168.90.23",30002],["192.168.90.23",30003]]
     */

    private String version;
    private List<List<String>> hosts;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<List<String>> getHosts() {
        return hosts;
    }

    public void setHosts(List<List<String>> hosts) {
        this.hosts = hosts;
    }
}
