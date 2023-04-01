package de.tomino.discordconsole.listener;

import de.tomino.discordconsole.DiscordConsole;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import me.lucko.spark.api.statistic.types.GenericStatistic;

public class ServerPerformanceListener {

    /**
     * Get the current TPS of the server.
     *
     * @return The current TPS.
     * @see me.lucko.spark.api.Spark#tps()
     */
    public String getServerTPS() {
        DoubleStatistic<StatisticWindow.TicksPerSecond> tps = DiscordConsole.spark.tps();
        return tps != null ? tps.toString() : null;
    }

    /**
     * Get the current MSPT of the server.
     *
     * @return The current MSPT.
     * @see me.lucko.spark.api.Spark#mspt()
     */
    public String getServerMSPT() {
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> mspt = DiscordConsole.spark.mspt();
        return mspt != null ? mspt.toString() : null;
    }

    /**
     * Get the current CPU usage of the server.
     *
     * @return The current CPU usage.
     * @see me.lucko.spark.api.Spark#cpuSystem()
     */
    public String getServerCPUUsage() {
        DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = DiscordConsole.spark.cpuSystem();
        return cpuUsage.toString();
    }

    /**
     * Get the current amount of memory in use on the JVM.
     *
     * @return The current amount converted to MB.
     */
    public String getServerMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory() / 1024 / 1024;
        return String.valueOf(usedMemory);
    }

    /**
     * Get the maximum amount of memory that the JVM will attempt to use.
     *
     * @return The maximum amount converted to MB.
     */
    public String getServerMemoryMax() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / 1024 / 1024;
        return String.valueOf(maxMemory);
    }
}
