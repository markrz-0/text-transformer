import React, { useState } from 'react';
import styled, { createGlobalStyle } from 'styled-components';
import {
  DndContext,
  DragOverlay,
  PointerSensor,
  KeyboardSensor,
  closestCenter,
  useDroppable,
  useSensor,
  useSensors,
} from '@dnd-kit/core';
import {
  arrayMove,
  SortableContext,
  sortableKeyboardCoordinates,
  useSortable,
  horizontalListSortingStrategy,
} from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';

const TRANSFORMATIONS = [
  { id: 'upper', label: 'Uppercase', description: 'Converts text to uppercase.' },
  { id: 'lower', label: 'Lowercase', description: 'Converts text to lowercase.' },
  { id: 'capitalize', label: 'Capitalize', description: 'Capitalizes words.' },
  { id: 'reverse', label: 'Reverse', description: 'Reverses the text.' },
  { id: 'latex', label: 'LaTeX', description: 'Formats text for LaTeX.' },
  { id: 'repeated-words', label: 'Repeated words', description: 'Detects repeated words.' },
  { id: 'number-to-text', label: 'Number to text', description: 'Changes numbers into words.' },
  { id: 'shorten', label: 'Shorten', description: 'Makes the text shorter.' },
  { id: 'expand', label: 'Expand', description: 'Makes the text longer.' },
];

function createQueueItem(type) {
  return {
    uid: `${type}-${Date.now()}-${Math.random().toString(16).slice(2)}`,
    type,
  };
}

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    min-height: 100vh;
    background:
      radial-gradient(circle at top left, rgba(100, 255, 218, 0.13), transparent 32rem),
      linear-gradient(135deg, #07111f 0%, #0a192f 48%, #111827 100%);
    color: #e5f7ff;
    font-family: Inter, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
  }

  button,
  textarea {
    font: inherit;
  }
`;

const Page = styled.main`
  min-height: 100vh;
  padding: 48px 20px;
  display: flex;
  justify-content: center;
`;

const Container = styled.section`
  width: min(1040px, 100%);
  display: grid;
  gap: 24px;
`;

const Hero = styled.header`
  padding: 34px;
  border: 1px solid rgba(148, 163, 184, 0.25);
  border-radius: 28px;
  background: rgba(15, 23, 42, 0.72);
  box-shadow: 0 24px 80px rgba(0, 0, 0, 0.28);
  backdrop-filter: blur(14px);

  h1 {
    margin: 0 0 10px;
    font-size: clamp(32px, 5vw, 54px);
    line-height: 1;
    letter-spacing: -0.05em;
  }

  p {
    max-width: 720px;
    margin: 0;
    color: #a7b6c9;
    line-height: 1.7;
  }
`;

const Grid = styled.div`
  display: grid;
  grid-template-columns: minmax(0, 1fr) 360px;
  gap: 24px;

  @media (max-width: 900px) {
    grid-template-columns: 1fr;
  }
`;

const Panel = styled.div`
  padding: 24px;
  border: 1px solid rgba(148, 163, 184, 0.22);
  border-radius: 24px;
  background: rgba(15, 23, 42, 0.72);
  box-shadow: 0 20px 55px rgba(0, 0, 0, 0.22);
`;

const SectionTitle = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;

  h2 {
    margin: 0;
    font-size: 20px;
    letter-spacing: -0.02em;
  }

  p {
    margin: 4px 0 0;
    color: #8ea0b7;
    font-size: 14px;
    line-height: 1.5;
  }
`;

const Counter = styled.span`
  flex-shrink: 0;
  padding: 7px 12px;
  border-radius: 999px;
  background: rgba(100, 255, 218, 0.1);
  border: 1px solid rgba(100, 255, 218, 0.28);
  color: #64ffda;
  font-size: 13px;
  font-weight: 700;
`;

const StyledTextArea = styled.textarea`
  width: 100%;
  min-height: 190px;
  resize: vertical;
  padding: 18px;
  border-radius: 18px;
  outline: none;
  background: rgba(2, 6, 23, 0.5);
  color: #e5f7ff;
  border: 1px solid rgba(148, 163, 184, 0.28);
  font-size: 16px;
  line-height: 1.6;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;

  &::placeholder {
    color: #64748b;
  }

  &:focus {
    border-color: rgba(100, 255, 218, 0.7);
    box-shadow: 0 0 0 4px rgba(100, 255, 218, 0.09);
  }
`;

const ActionsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;

  @media (max-width: 520px) {
    grid-template-columns: 1fr;
  }
`;

const ActionButton = styled.button`
  min-height: 74px;
  padding: 14px;
  border: 1px solid rgba(100, 255, 218, 0.28);
  border-radius: 16px;
  cursor: pointer;
  text-align: left;
  color: #dffcf7;
  background: rgba(17, 34, 64, 0.9);
  transition: transform 0.18s ease, border-color 0.18s ease, background 0.18s ease;

  strong {
    display: block;
    margin-bottom: 4px;
    font-size: 14px;
  }

  span {
    display: block;
    color: inherit;
    opacity: 0.72;
    font-size: 12px;
    line-height: 1.35;
  }

  &:hover {
    transform: translateY(-2px);
    border-color: #64ffda;
    background: rgba(31, 56, 95, 0.95);
  }
`;

const QueueWrapper = styled.div`
  display: grid;
  gap: 16px;
`;

const QueueBox = styled.div`
  min-height: 98px;
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 12px;
  overflow-x: auto;
  border-radius: 20px;
  border: 1px solid rgba(148, 163, 184, 0.24);
  background: rgba(2, 6, 23, 0.42);
  scrollbar-width: thin;
`;

const EmptyState = styled.div`
  width: 100%;
  min-height: 64px;
  display: grid;
  place-items: center;
  border-radius: 16px;
  border: 1px dashed rgba(148, 163, 184, 0.32);
  color: #8493a8;
  font-size: 14px;
`;

const Tile = styled.div`
  width: 170px;
  min-width: 170px;
  max-width: 170px;
  padding: 13px 14px;
  display: flex;
  align-items: center;
  gap: 11px;
  border: 1px solid ${({ $dragging }) =>
    $dragging ? '#64ffda' : 'rgba(100, 255, 218, 0.32)'};
  border-radius: 16px;
  background: ${({ $dragging }) =>
    $dragging ? 'rgba(100, 255, 218, 0.16)' : 'rgba(17, 34, 64, 0.95)'};
  color: #64ffda;
  box-shadow: ${({ $dragging }) =>
    $dragging
      ? '0 20px 45px rgba(0, 0, 0, 0.3)'
      : '0 10px 24px rgba(0, 0, 0, 0.14)'};
  opacity: ${({ $sorting }) => ($sorting ? 0.45 : 1)};
  user-select: none;
  touch-action: none;
  overflow: hidden;
`;

const DragHandle = styled.button`
  width: 32px;
  height: 32px;
  flex-shrink: 0;
  display: grid;
  place-items: center;
  border: 0;
  border-radius: 11px;
  background: rgba(100, 255, 218, 0.12);
  color: #b7fff0;
  cursor: grab;

  &:active {
    cursor: grabbing;
  }
`;

const TileText = styled.div`
  min-width: 0;
  flex: 1;
  overflow: hidden;

  strong {
    display: block;
    color: #dffcf7;
    font-size: 14px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  span {
    display: block;
    margin-top: 2px;
    color: #8ea0b7;
    font-size: 12px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;

const TrashArea = styled.div`
  min-height: 82px;
  display: grid;
  place-items: center;
  padding: 18px;
  border: 2px dashed ${({ $active }) =>
    $active ? '#ff7b7b' : 'rgba(255, 122, 122, 0.45)'};
  color: ${({ $active }) => ($active ? '#ffd1d1' : '#ff9a9a')};
  border-radius: 18px;
  text-align: center;
  background: ${({ $active }) =>
    $active ? 'rgba(255, 77, 77, 0.18)' : 'rgba(255, 77, 77, 0.08)'};
  transition: background 0.18s ease, border-color 0.18s ease, color 0.18s ease;
`;

const ExecuteButton = styled.button`
  width: 100%;
  margin-top: 18px;
  padding: 15px 18px;
  border: 0;
  border-radius: 16px;
  cursor: pointer;
  background: #64ffda;
  color: #07111f;
  font-weight: 800;
  transition: transform 0.18s ease, filter 0.18s ease;

  &:hover {
    transform: translateY(-1px);
    filter: brightness(1.05);
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.55;
    transform: none;
  }
`;

const ResultBox = styled.div`
  min-height: 130px;
  margin-top: 18px;
  padding: 18px;
  border-radius: 18px;
  border: 1px solid rgba(148, 163, 184, 0.22);
  background: rgba(2, 6, 23, 0.44);
  color: #dbeafe;
  line-height: 1.65;
  white-space: pre-wrap;
  overflow-wrap: anywhere;
`;

const HelperText = styled.p`
  margin: 10px 0 0;
  color: #8ea0b7;
  font-size: 13px;
  line-height: 1.5;
`;

function getTransformation(type) {
  return (
    TRANSFORMATIONS.find((item) => item.id === type) || {
      id: type,
      label: type,
      description: 'Custom transformation.',
    }
  );
}

function DroppableTrash({ id }) {
  const { setNodeRef, isOver } = useDroppable({ id });

  return (
    <TrashArea ref={setNodeRef} $active={isOver}>
      {isOver ? 'Release to remove this step' : 'Drag a step here to delete it'}
    </TrashArea>
  );
}

function SortableTile({ item }) {
  const transformation = getTransformation(item.type);

  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
    isDragging,
  } = useSortable({ id: item.uid });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  return (
    <Tile ref={setNodeRef} style={style} $sorting={isDragging}>
      <DragHandle
        type="button"
        {...attributes}
        {...listeners}
        aria-label={`Drag ${transformation.label}`}
      >
        ☰
      </DragHandle>

      <TileText title={transformation.label}>
        <strong>{transformation.label}</strong>
        <span>{transformation.id}</span>
      </TileText>
    </Tile>
  );
}

function TilePreview({ item }) {
  if (!item) return null;

  const transformation = getTransformation(item.type);

  return (
    <Tile $dragging>
      <DragHandle as="div">☰</DragHandle>

      <TileText title={transformation.label}>
        <strong>{transformation.label}</strong>
        <span>{transformation.id}</span>
      </TileText>
    </Tile>
  );
}

function App() {
  const [text, setText] = useState('');
  const [result, setResult] = useState('');
  const [pipeline, setPipeline] = useState([createQueueItem('upper')]);
  const [activeId, setActiveId] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const activeItem = pipeline.find((item) => item.uid === activeId);

  const sensors = useSensors(
    useSensor(PointerSensor, {
      activationConstraint: {
        distance: 8,
      },
    }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    })
  );

  function addTransformation(type) {
    setPipeline((current) => [...current, createQueueItem(type)]);
  }

  function handleDragStart(event) {
    setActiveId(event.active.id);
  }

  function handleDragCancel() {
    setActiveId(null);
  }

  function handleDragEnd(event) {
    const { active, over } = event;

    setActiveId(null);

    if (!over) return;

    if (over.id === 'trash') {
      setPipeline((current) => current.filter((item) => item.uid !== active.id));
      return;
    }

    if (active.id === over.id) return;

    setPipeline((current) => {
      const oldIndex = current.findIndex((item) => item.uid === active.id);
      const newIndex = current.findIndex((item) => item.uid === over.id);

      if (oldIndex === -1 || newIndex === -1) {
        return current;
      }

      return arrayMove(current, oldIndex, newIndex);
    });
  }

  async function handleTransform() {
    if (!text.trim()) {
      setResult('Enter text before running the transformation.');
      return;
    }

    if (pipeline.length === 0) {
      setResult('Add at least one transformation to the queue.');
      return;
    }

    setIsLoading(true);
    setResult('');

    try {
      const res = await fetch('/transform', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          text,
          transformation: pipeline.map((item) => item.type),
        }),
      });

      const data = await res.json();

      if (!res.ok) {
        throw new Error(data.error || 'Transformation failed.');
      }

      setResult(data.text || 'No result returned.');
    } catch (error) {
      setResult(error.message || 'Something went wrong.');
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <Page>
      <GlobalStyle />

      <Container>
        <Hero>
          <h1>Text Transformer</h1>
          <p>
            Build a clean transformation queue, reorder steps by dragging the
            handle, and remove unnecessary steps by dropping them into the
            delete area.
          </p>
        </Hero>

        <Grid>
          <Panel>
            <SectionTitle>
              <div>
                <h2>Input text</h2>
                <p>
                  Paste the content that should be processed by the selected
                  transformations.
                </p>
              </div>
            </SectionTitle>

            <StyledTextArea
              value={text}
              onChange={(event) => setText(event.target.value)}
              placeholder="Enter text..."
            />

            <SectionTitle style={{ marginTop: 24 }}>
              <div>
                <h2>Transformation queue</h2>
                <p>
                  Drag by the handle. The same transformation can be added many
                  times.
                </p>
              </div>

              <Counter>{pipeline.length} selected</Counter>
            </SectionTitle>

            <DndContext
              sensors={sensors}
              collisionDetection={closestCenter}
              onDragStart={handleDragStart}
              onDragCancel={handleDragCancel}
              onDragEnd={handleDragEnd}
            >
              <QueueWrapper>
                <QueueBox>
                  {pipeline.length === 0 ? (
                    <EmptyState>No transformations selected yet.</EmptyState>
                  ) : (
                    <SortableContext
                      items={pipeline.map((item) => item.uid)}
                      strategy={horizontalListSortingStrategy}
                    >
                      {pipeline.map((item) => (
                        <SortableTile key={item.uid} item={item} />
                      ))}
                    </SortableContext>
                  )}
                </QueueBox>

                <DroppableTrash id="trash" />
              </QueueWrapper>

              <DragOverlay>
                {activeItem ? <TilePreview item={activeItem} /> : null}
              </DragOverlay>
            </DndContext>

            <ExecuteButton onClick={handleTransform} disabled={isLoading}>
              {isLoading ? 'Processing...' : 'Execute transformation'}
            </ExecuteButton>

            <ResultBox>
              {result || 'The transformed text will appear here.'}
            </ResultBox>
          </Panel>

          <Panel>
            <SectionTitle>
              <div>
                <h2>Available transformations</h2>
                <p>
                  Click a transformation to add it to the queue. You can add the
                  same transformation more than once.
                </p>
              </div>
            </SectionTitle>

            <ActionsGrid>
              {TRANSFORMATIONS.map((item) => (
                <ActionButton
                  key={item.id}
                  type="button"
                  onClick={() => addTransformation(item.id)}
                >
                  <strong>+ {item.label}</strong>
                  <span>{item.description}</span>
                </ActionButton>
              ))}
            </ActionsGrid>

            <HelperText>
              Each added item receives a unique internal ID, so repeated
              transformations can still be dragged and deleted independently.
            </HelperText>
          </Panel>
        </Grid>
      </Container>
    </Page>
  );
}

export default App;