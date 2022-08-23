import java.util.ArrayList;
import java.util.List;

public class TextAnalyticsRequest {

	private List<TextDocument> documents = new ArrayList<>();

	public List<TextDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<TextDocument> documents) {
		this.documents = documents;
	}

	public void add(TextDocument textDocument){
		documents.add(textDocument);
	}

	public boolean isEmpty(){
		return documents.isEmpty();
	}

}
