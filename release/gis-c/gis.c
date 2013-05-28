#include <igraph.h>
#include <stdio.h>
#include <stdlib.h>

void print_and_destroy_cliques(igraph_vector_ptr_t *cliques) 
{
	int i;
	for (i = 0; i < igraph_vector_ptr_size(cliques); i++) 
	{
		igraph_vector_t *v = VECTOR(*cliques)[i];
		igraph_vector_sort(v);
		igraph_vector_print(v);
		igraph_vector_destroy(v);
		igraph_free(v);
	}
}

int main(int argc, char **argv)
{  
	if (argc < 2)
	{
		printf("Usage: %s input_file.txt\n", argv[0]);
		exit(1);
	}
	
	const char *file = argv[1];
	FILE *infile = fopen(file, "r");
	igraph_t g;

	if (!infile) 
	{
		printf("Cannot open file: %s\n", file);
		exit(2);
	}
	
	igraph_read_graph_dl(&g, infile, 0);
	
	int ret = fclose(infile);
	if (ret) 
	{
		printf("Cannot close file: %s\n", file);
		exit(3);
	}
	
	igraph_vector_ptr_t cliques;
	igraph_vector_ptr_init(&cliques, 0);
	ret = igraph_largest_cliques(&g, &cliques);
	if (ret)
	{
		printf("Error during finding the largest cliques.\n");
		exit(4);
	}
	
	print_and_destroy_cliques(&cliques);
	
	igraph_vector_ptr_destroy(&cliques);
	igraph_destroy(&g);

	return 0;
}
