package teamup.rivile.com.teamup.Project.Details;

import java.util.List;

import teamup.rivile.com.teamup.Uitls.APIModels.AttachmentModel;

public class OfferDetailsAttachment {
    public List<AttachmentModel> Attachments;

    public OfferDetailsAttachment(List<AttachmentModel> attachments) {
        Attachments = attachments;
    }

    public OfferDetailsAttachment() {
    }

    public List<AttachmentModel> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<AttachmentModel> attachments) {
        Attachments = attachments;
    }
}
