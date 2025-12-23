package ru.yp.sprint5pw.controller.dto;

public class ItemDto {
    private Integer id;
    private String title;
    private String description;
    private String imgPath;
    private Long price = 0L;
    int count = 0;

    public ItemDto () {
    }

    public ItemDto(Integer id, String title, String description, String imgPath, Long price, int count) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
