package com.kd.higit.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KD on 2016/6/24.
 */
public class FileOrDirContent implements Parcelable {
    public String type;
    public int size;
    public String name;
    public String path;
    public String sha;
    public String url;
    public String git_url;
    public String html_url;
    public String download_url;
    public Links _links;
    public String encoding;
    public String content;

    public FileOrDirContent() {
    }

    public FileOrDirContent(Parcel in) {
        readFromParcel(in);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(size);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(sha);
        dest.writeString(url);
        dest.writeString(git_url);
        dest.writeString(html_url);
        dest.writeString(download_url);
        dest.writeParcelable(_links, flags);
        dest.writeString(encoding);
        dest.writeString(content);
    }

    public void readFromParcel(Parcel in) {
        type = in.readString();
        size = in.readInt();
        name = in.readString();
        path = in.readString();
        sha = in.readString();
        url = in.readString();
        git_url = in.readString();
        html_url = in.readString();
        download_url = in.readString();
        _links = in.readParcelable(Links.class.getClassLoader());
        encoding = in.readString();
        content = in.readString();
    }

    public static final Parcelable.Creator<FileOrDirContent> CREATOR = new Creator<FileOrDirContent>() {
        @Override
        public FileOrDirContent createFromParcel(Parcel source) {
            return new FileOrDirContent(source);
        }

        @Override
        public FileOrDirContent[] newArray(int size) {
            return new FileOrDirContent[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
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

    public boolean isDir() {
        return "dir".equals(type);
    }

    public boolean isFile() {
        return "file".equals(type);
    }
}
