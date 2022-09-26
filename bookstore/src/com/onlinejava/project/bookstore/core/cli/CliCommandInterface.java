package com.onlinejava.project.bookstore.core.cli;

public interface CliCommandInterface {

    String getCommandID();

    String getTitle();

    String getDescription();

    public void run();

    public default int order() {
        return Integer.MAX_VALUE;
    }

    public static int ordering(CliCommandInterface c1, CliCommandInterface c2) {
        if (c1.order() != c2.order()) {
            return c1.order() - c2.order();
        }

        boolean isC1Number = c1.getCommandID().matches("\\d+");
        boolean isC2Number = c2.getCommandID().matches("\\d+");
//        boolean isC1Number = Pattern.compile("\\d+").matcher(c1.getCommandID()).matches();
//        boolean isC2Number = Pattern.compile("\\d+").matcher(c2.getCommandID()).matches();
        if (isC1Number && isC2Number) {
            return Integer.parseInt(c1.getCommandID()) - Integer.parseInt(c2.getCommandID());
        } else if (isC1Number) {
            return -1;
        } else if (isC2Number) {
            return 1;
        } else {
            return c1.getCommandID().compareTo(c2.getCommandID());
        }
    }
}
