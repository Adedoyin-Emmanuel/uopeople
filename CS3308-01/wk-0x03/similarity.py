import math

# Define corporate collection and query
query = ["information", "retrieval", "techniques"]
d1 = ["advanced", "information", "retrieval", "methods"]
d2 = ["data", "mining", "techniques", "for", "analysis"]

# Build unique vocabulary
vocab = sorted(list(set(query + d1 + d2)))

def get_tf_vector(doc, vocabulary):
    return [doc.count(term) for term in vocabulary]

# Generate raw term frequency vectors
v_q = get_tf_vector(query, vocab)
v_d1 = get_tf_vector(d1, vocab)
v_d2 = get_tf_vector(d2, vocab)

def compute_cosine(vec1, vec2):
    dot_product = sum(a * b for a, b in zip(vec1, vec2))
    mag1 = math.sqrt(sum(a * a for a in vec1))
    mag2 = math.sqrt(sum(b * b for b in vec2))
    if mag1 == 0 or mag2 == 0:
        return 0.0
    return dot_product / (mag1 * mag2)

sim_d1 = compute_cosine(v_q, v_d1)
sim_d2 = compute_cosine(v_q, v_d2)

print(f"Vocabulary: {vocab}")
print(f"Query Vector: {v_q}")
print(f"D1 Vector:    {v_d1}")
print(f"D2 Vector:    {v_d2}")
print(f"Cosine Similarity D1: {sim_d1:.4f}")
print(f"Cosine Similarity D2: {sim_d2:.4f}")