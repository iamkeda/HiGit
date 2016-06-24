package com.kd.higit.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KD on 2016/6/23.
 */
public class ReadmeContent implements Parcelable {
    public String name;
    public String path;
    public String sha;
    public int size;
    public String html_url;
    public String git_url;
    public String download_url;
    public String type;
    public String content;
    public String encoding;
    public Links _links;

    public ReadmeContent() {
    }

    public ReadmeContent(Parcel in ) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(sha);
        dest.writeInt(size);
        dest.writeString(html_url);
        dest.writeString(git_url);
        dest.writeString(download_url);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(encoding);
        dest.writeParcelable(_links, flags);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        path = in.readString();
        sha = in.readString();
        size = in.readInt();
        html_url = in.readString();
        git_url = in.readString();
        download_url = in.readString();
        type = in.readString();
        content = in.readString();
        encoding = in.readString();
        _links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReadmeContent> CREATOR = new Creator<ReadmeContent>() {
        @Override
        public ReadmeContent createFromParcel(Parcel source) {
            return new ReadmeContent(source);
        }

        @Override
        public ReadmeContent[] newArray(int size) {
            return new ReadmeContent[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
