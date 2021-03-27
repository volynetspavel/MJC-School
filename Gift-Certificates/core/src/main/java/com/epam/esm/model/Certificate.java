package com.epam.esm.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Certificate is entity of certificate.
 */
public class Certificate extends AbstractEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createTime;
    private String lastUpdateTime;

    public Certificate(){}

    public Certificate(int id, String name, String description, BigDecimal price, Integer duration,
                       String createTime, String lastUpdateTime) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return name.equals(that.name) &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                duration.equals(that.duration) &&
                createTime.equals(that.createTime) &&
                lastUpdateTime.equals(that.lastUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, duration, createTime, lastUpdateTime);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration='" + duration + '\'' +
                ", createTime='" + createTime + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}
