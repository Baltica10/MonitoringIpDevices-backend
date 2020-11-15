package com.krasnov.monitoring.model.device

import com.krasnov.monitoring.model.reports.*
import javax.persistence.*

@Entity
@Table(name = "devices")
data class Device(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "type_id")
        val type: DeviceType,

        @ManyToOne
        @JoinColumn(name = "brand_id")
        val brand: DeviceBrand,

        val model: String,

        @Column(name = "serial_number")
        val serialNumber: String,

        val description: String?,

        val image: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "settings_id")
        val settings: DeviceSettings?,

        @Column(name = "check_available")
        val checkAvailable: Boolean,

        @Column(name = "check_page_count")
        val checkPageCount: Boolean,

        @Column(name = "notify_tlg")
        val notifyTlg: Boolean,

        @Column(name = "notify_email")
        val notifyEmail: Boolean,

        @OneToMany(mappedBy = "device",
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY)
        var availableReports: Set<DeviceAvailableReport>?,

        @OneToMany(mappedBy = "device",
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                fetch = FetchType.LAZY)
        var pageCounts: Set<DevicePageCountReport>?
) {
    fun getCheckAvailableURL(): String {
        return this.settings?.protocol?.value +
                this.settings?.host
    }

    fun getPageCountURL(): String {
        return this.settings?.protocol?.value +
                this.settings?.host +
                this.settings?.pageCountURL?.value
    }
}