package entry;

import javax.validation.constraints.NotNull;

public class PhotoEntry extends AbstractDataEntry {
    
    @NotNull(message = "photoRef cannot be null")
    private String photoRef;
    
    private IdeaEntry idea;
    
    private ImplementationInfoEntry implementationInfo;

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public IdeaEntry getIdea() {
        return idea;
    }

    public void setIdea(IdeaEntry idea) {
        this.idea = idea;
    }

    public ImplementationInfoEntry getImplementationInfo() {
        return implementationInfo;
    }

    public void setImplementationInfo(ImplementationInfoEntry implementationInfo) {
        this.implementationInfo = implementationInfo;
    }

}
